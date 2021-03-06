package juuxel.adorn.debug

import com.mojang.brigadier.arguments.StringArgumentType
import juuxel.adorn.config.AdornConfigManager
import net.fabricmc.fabric.api.registry.CommandRegistry
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier

/**
 * Debugging features.
 *
 * As of Adorn 1.4.0:
 * - `/adorn resource`: a command to get display a resource from the resource manager as a string
 */
object Debug {
    fun shouldLoad() = AdornConfigManager.CONFIG.debug

    fun init() {
        CommandRegistry.INSTANCE.register(false) { dispatcher ->
            dispatcher.register(
                literal("adorn").then(
                    literal("resource").then(
                        argument("name", StringArgumentType.greedyString()).executes { context ->
                            Identifier.tryParse(StringArgumentType.getString(context, "name"))?.let { id ->
                                try {
                                    context.source.minecraftServer.dataManager.getResource(id).use { resource ->
                                        resource.inputStream.use { input ->
                                            input.bufferedReader().lines().forEach {
                                                context.source.sendFeedback(LiteralText(it), false)
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    context.source.sendError(LiteralText(e.message))
                                    e.printStackTrace()
                                }
                            }
                            0
                        }
                    )
                )
            )
        }
    }
}
