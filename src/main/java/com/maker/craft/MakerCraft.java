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



public class MakerCraft implements ModInitializer {


	Integer braco1value = 0;

	SerialPort comPort;

	@Override
	public void onInitialize() {

		comPort = Arduino.Connect();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("braco")
					.then(CommandManager.argument("id", IntegerArgumentType.integer(1))
							.then(CommandManager.literal("add")
									.then(CommandManager.argument("valor", IntegerArgumentType.integer(1))
										.executes(ctx -> executarBraco(ctx, true))))
							.then(CommandManager.literal("remove")
									.then(CommandManager.argument("valor", IntegerArgumentType.integer(1))
										.executes(ctx -> executarBraco(ctx, false))))
					));
		});


		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Arduino.close();
		}));
	}

	private int executarBraco(CommandContext<ServerCommandSource> context, boolean adicionar) throws CommandSyntaxException {
		int id = IntegerArgumentType.getInteger(context, "id");
		int valor = IntegerArgumentType.getInteger(context, "valor");


		if(adicionar && braco1value < 120){
			braco1value += valor;
		}else if(!adicionar && braco1value > 0){
			braco1value -= valor;
		}

		context.getSource().getServer().getPlayerManager().broadcast(
				Text.literal("Braço com ID " + id + ": " + braco1value),
				false
		);

		Arduino.sendSerialMessage(comPort, braco1value.toString());
		

		// Aqui você pode chamar sua lógica: exemplo, ArduinoController.setBraco(id, adicionar)

		return 1;
	}
}
