package com.maker.craft;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.message.SentMessage;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class MakerCraft implements ModInitializer {

	int braco1value = 0;

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("braco")
					.then(CommandManager.argument("id", IntegerArgumentType.integer(1))
							.then(CommandManager.literal("add")
									.executes(ctx -> executarBraco(ctx, true)))
							.then(CommandManager.literal("remove")
									.executes(ctx -> executarBraco(ctx, false)))
					));
		});
	}

	private int executarBraco(CommandContext<ServerCommandSource> context, boolean adicionar) throws CommandSyntaxException {
		int id = IntegerArgumentType.getInteger(context, "id");

		if(adicionar && braco1value < 180){
			braco1value += 1;
		}else if(!adicionar && braco1value > 0){
			braco1value -= 1;
		}

		context.getSource().getServer().getPlayerManager().broadcast(
				Text.literal("Braço com ID " + id + ": " + braco1value),
				false
		);


		// Aqui você pode chamar sua lógica: exemplo, ArduinoController.setBraco(id, adicionar)

		return 1;
	}
}
