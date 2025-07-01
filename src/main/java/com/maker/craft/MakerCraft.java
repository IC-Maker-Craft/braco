package com.maker.craft;

import com.fazecast.jSerialComm.SerialPort;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;


public class MakerCraft implements ModInitializer {

	private final Map<Integer, Integer> posicoes = new HashMap<>();


	SerialPort comPort;

	@Override
	public void onInitialize() {


		comPort = Arduino.Connect();


		// isso cria um comando dentro do mine, ex: /braco 18 add 30, esse comando mexe o servo no pino 18, aumentando 30 graus

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("braco")
					.then(CommandManager.argument("porta", IntegerArgumentType.integer(1))
							.then(CommandManager.literal("add")
									.then(CommandManager.argument("valor", IntegerArgumentType.integer(1))
										.executes(ctx -> executarBraco(ctx, true))))
							.then(CommandManager.literal("remove")
									.then(CommandManager.argument("valor", IntegerArgumentType.integer(1))
										.executes(ctx -> executarBraco(ctx, false))))
					));
		});


		// fecha a porta serial do servo
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Arduino.close();
		}));
	}



	private int executarBraco(CommandContext<ServerCommandSource> context, boolean adicionar) throws CommandSyntaxException {
		// assim que executar o comando esse bloco de codigo, pegando os paramtros e usando o medoto de enviar mensagem pro arduino
		int porta = IntegerArgumentType.getInteger(context, "porta");
		int valor = IntegerArgumentType.getInteger(context, "valor");

		// valor atual default é 0 se não houver ainda
		int atual = posicoes.getOrDefault(porta, 0);

		// atualiza posição
		if (adicionar && atual + valor <= 180) {
			atual += valor;
		} else if (!adicionar && atual - valor >= 0) {
			atual -= valor;
		}

		posicoes.put(porta, atual);

		// envia comando como: P21:90 (P de "porta")
		String mensagem = "P" + porta + ":" + atual;
		Arduino.sendSerialMessage(comPort, mensagem);

		// manda no chat
		context.getSource().getServer().getPlayerManager().broadcast(
				Text.literal("Servo no pino " + porta + " -> " + atual + " graus"),
				false
		);

		return 1;
	}

}
