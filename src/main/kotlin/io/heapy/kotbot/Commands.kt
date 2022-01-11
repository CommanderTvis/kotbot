package io.heapy.kotbot

import io.heapy.kotbot.Command.Access
import io.heapy.kotbot.Command.Context
import io.heapy.kotbot.bot.Method
import io.heapy.kotbot.bot.SendMessage
import io.heapy.kotbot.bot.Update
import io.heapy.kotbot.bot.Update.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

interface Command {
    val name: String
    val context: Context
    val access: Access

    fun execute(
        update: Update
    ): Flow<Method<*>>

    /**
     * Non-null access to message,
     * that should be present because previous checks.
     */
    val Update.cmdMessage: Message
        get() = message!!

    enum class Context {
        USER_CHAT,
        GROUP_CHAT
    }

    enum class Access {
        USER,
        ADMIN
    }
}

object NoopCommand : Command {
    override val name = "/noop"
    override val context = Context.USER_CHAT
    override val access = Access.ADMIN

    override fun execute(
        update: Update
    ): Flow<Method<*>> {
        return flowOf()
    }
}

class HelloWorldCommand : Command {
    override val name = "/hello"
    override val context = Context.USER_CHAT
    override val access = Access.ADMIN

    override fun execute(
        update: Update
    ): Flow<Method<*>> {
        return flowOf(
            SendMessage(
                chat_id = update.cmdMessage.chat.id.toString(),
                text = "Hello, World!"
            )
        )
    }
}

class ChatInfoCommand : Command {
    override val name = "/chat-info"
    override val context = Context.GROUP_CHAT
    override val access = Access.ADMIN

    override fun execute(
        update: Update
    ): Flow<Method<*>> {
        return flowOf(
            SendMessage(
                chat_id = update.cmdMessage.chat.id.toString(),
                text = """
                Chat id: ${update.cmdMessage.chat.id}
            """.trimIndent()
            )
        )
    }
}

class SpamCommand : Command {
    override val name = "/spam"
    override val context = Context.GROUP_CHAT
    override val access = Access.ADMIN

    override fun execute(
        update: Update
    ): Flow<Method<*>> {
        update.cmdMessage.reply_to_message?.let { reply ->
            return flowOf(
                reply.delete,
                reply.banFrom
            )
        }

        return emptyFlow()
    }
}

class SendMessageFromBotCommand(
    private val admin: Long,
    override val name: String,
    val id: Long,
) : Command {
    override val context = Context.USER_CHAT
    override val access = Access.ADMIN

    override fun execute(
        update: Update
    ): Flow<Method<*>> {
        update.cmdMessage.text
            ?.split(' ', limit = 2)
            ?.getOrNull(1)
            ?.let { text ->
                return flowOf(
                    SendMessage(
                        chat_id = id.toString(),
                        text = text
                    ),
                    SendMessage(
                        chat_id = admin.toString(),
                        text = """
                            ${update.cmdMessage.from?.username} sent following message to chat $name:
                            $text
                        """.trimIndent()
                    )
                )
            }

        return emptyFlow()
    }
}
