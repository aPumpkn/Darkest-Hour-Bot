package pumpkin.bot.darkest_hour.core;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import pumpkin.bot.darkest_hour.core.tasks.ChatFilter;
import pumpkin.bot.darkest_hour.core.tasks.Command;
import pumpkin.bot.darkest_hour.core.tasks.Verify;
import pumpkin.bot.darkest_hour.core.utils.BotFile;
import pumpkin.bot.darkest_hour.core.utils.Response;
import pumpkin.bot.darkest_hour.core.utils.Tool;

// Reacts to certain events that the bot listens to.
public class Listener extends ListenerAdapter {
	
	/* JDA Default Entities */
	public JDA jda; // Core of all entities.
	public Guild guild; // A Server hosted by Discord.
	public Category category; // Organizes a Guild's channels in a folder-fashion.
	public TextChannel channel; // Default channel the event was fired from.
	public Message message; // Text Message sent to Discord.
	public User user; // Discord account.
	public Member member; // Guild-specific User.
	
	/* JDA TextChannels */
	public TextChannel generalChannel; // #general
	public TextChannel beginnersguideChannel; // #beginners-guide
	public TextChannel modlogChannel; // #mod-log
	
	/* JDA Roles */
	public Role unverifiedRole; // @Unverified
	
	/* JDA Events */
	public GuildMemberJoinEvent guildMemberJoinEvent;
	public GuildMemberLeaveEvent guildMemberLeaveEvent;
	public GuildBanEvent guildBanEvent;
	public TextChannelCreateEvent textChannelCreateEvent;
	public GuildMessageReceivedEvent guildMessageReceivedEvent;
	
	/* JDA Utils */
	public GuildController controller; // Performs audit-level actions to a Guild (Server).
	
	/* Java Utils */
	public PrintWriter writer; // Creates or overwrites a file.
	public Scanner scanner; // Reads the contents of a file.
	public Timer timer = new Timer("Listener"); // Delays the contained code block for a specified amount.

	/* Custom Utils */
	public Verify verify; // Verification system for the bot. Uses human logic.
	public BotFile botFile; // Reads and interacts with files from the bot.
	public Tool tool; // List of useful tools the bot uses for menial tasks.
	public Response response; // Contains all bot responses.
	public ChatFilter filter; // Filters out and deletes specific messages sent to a Guild.
	public Command command; // Interprets a given command and performs the action requested.
	
	/* Other */
	public String memberTag; // Tag of the user to indicate who the bot is responding to.
	public String content; // Raw message sent by the user
	public String userId; // ID of the user
	public String botMessage; // Response the bot will return
	public String categoryName; // Name of the category;
	public String channelName; // Name of the channel
	public boolean isReady = false; // If it has initialized all the fields it contains
	public boolean isVerifyTicket = false; // If this newly created channel is a verify ticket
	
	
	
		/* JDA EVENTS */
	
	/* Fires upon anything detectable by the bot. Its the top-level of all
	 * other events, meaning they derive their information from this. This
	 * is used to initialize unchanging entities, such as Guild and
	 * specified TextChannel/Role objects, as well as per-event entities
	 * and other fields.
	 * 
	 * Parameters:
	 * event - a number that represents the event type.
	 */
	public void init(int code) {

		isVerifyTicket = false;
		
		if (!isReady) {
			
			response = new Response();
			tool = new Tool();
			verify = new Verify();
			guild = jda.getGuildById(555972965955665929l);
			controller = guild.getController();
			unverifiedRole = guild.getRoleById(613983789093355520l);
			generalChannel = guild.getTextChannelById(614011250694815753l);
			beginnersguideChannel = guild.getTextChannelById(555977448639037441l);
			modlogChannel = guild.getTextChannelById(614750983498760193l);
			isReady = true;
			
		}
		
		switch (code) {
			
			case 0: // guildMemberJoinEvent
				member = guildMemberJoinEvent.getMember();
				userId = user.getId();
				verify = new Verify();
				isVerifyTicket = true;
				break;
				
			case 1: // guildMemberLeaveEvent
				break;
				
			case 2: // guildBanEvent
				break;
				
			case 3: // textChannelCreateEvent
				channel = guild.getTextChannelsByName(userId, false).get(0);
				break;
				
			case 4: // guildMessageReceivedEvent
				message = guildMessageReceivedEvent.getMessage();
				channel = guildMessageReceivedEvent.getChannel();
				member = guildMessageReceivedEvent.getMember();
				category = message.getCategory();
				memberTag = "**(" + member.getEffectiveName() + ")** ";
				channelName = channel.getName();
				categoryName = category.getName();
				content = message.getContentRaw();
				userId = user.getId();
				botFile = new BotFile("profiles/" + userId + ".verify");
				break;
			
		}
		
	}
	
	/* Fires when a new member joins the guild. Creates a verification process
	 * for the user to follow, storing required data to a file for later access
	 * when that specific user responds, which will be continued in the
	 * onGuildMessageReceived event.
	 *
	 * Parameters:
	 * event - class containing the information pertaining to this event
	 */
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {

		guildMemberJoinEvent = event;
		user = event.getUser();
		
		if (!user.isBot()) {

			init(0);
			
			new BotFile("profiles/" + userId + ".verify").create("Question: " + verify.getQuestion() + "\r\nAnswer: " + verify.getAnswer());
			controller.addSingleRoleToMember(member, unverifiedRole).queue();
			botMessage = response.getWelcome() + verify.getQuestion();
			controller.createTextChannel(userId).queue();
			
		}
		
	}
	
	/* Fires when a member has left the guild.
	 * 
	 * Parameters:
	 * event - class containing the information pertaining to this event
	 */
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		
		guildMemberLeaveEvent = event;
		
	}
	
	/* Fires when a member of a guild is banned.
	 * 
	 * Parameters:
	 * event - class containing the information pertaining to this event
	 */
	@Override
	public void onGuildBan(GuildBanEvent event) {
		
		guildBanEvent = event;
		
	}

	@Override
	public void onTextChannelCreate(TextChannelCreateEvent event) {

		textChannelCreateEvent = event;
		
		if (isVerifyTicket) {

			init(3);
			
			channel.getManager().setSlowmode(30).queue();
			channel.putPermissionOverride(member).setAllow(Permission.MESSAGE_WRITE).queue();
			sendGuildMessage(channel, botMessage, false);
			
		}
		
	}
	
	/* Fires when a guild recieves a message.
	 * 
	 * Parameters:
	 * event - class containing the information pertaining to this event
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		guildMessageReceivedEvent = event;
		user = event.getAuthor();
		
		if (!user.isBot()) {

			init(4);
			
			if (botFile.exists()) { // If the user needs to be verified
					
				if (verify.check(content, botFile.find("answer"))) { // If the user gave the correct answer

					botFile.delete();
					new BotFile("profiles/registered/" + userId + "/").create();
					new BotFile("profiles/registered/" + userId + "/prefs.ini").create();
					new BotFile("profiles/registered/" + userId + "/profile.info").create();
					controller.removeSingleRoleFromMember(member, guild.getRoleById(613983789093355520l)).queue();
					channel.delete().queue();
					sendGuildMessage(response.getNewMember(member.getAsMention(), beginnersguideChannel.getAsMention()), false);
					
				} else sendGuildMessage(response.getVerifyFailure(), false);
				
			} else {
				
				if (new ChatFilter(content, channelName).check()) { // If this isn't an Rp channel and the message contained a blacklisted word/phrase

					message.delete().queue();
					sendGuildMessage(modlogChannel, response.getBlockedMessage(channel.getAsMention(), member.getAsMention(), content), false);
					
				} else {
					
					if (channelName.equals("bot-commands") && content.startsWith("$")) { // If this is a command
						
						command = new Command(message);
						
						if (command.resolve()); // If the command is valid
						else command.getError();
						
					}
					
				}
				
			}
			
		}
		
	}
	
	
	
		/* - CUSTOM METHODS - */
	
	/* Sends a message to the default channel.
	 * 
	 * Parameters:
	 * content - Message being sent
	 */
	public void sendGuildMessage(String content, boolean willTag) {
		
		if (willTag) channel.sendMessage(memberTag + content).queue();
		else channel.sendMessage(content).queue();
		
	}
	
	/* Sends a message to the specified channel.
	 * 
	 * Parameters:
	 * channel - Channel that the message will be sent to
	 * content - Message being sent
	 * willTag - If the message will tag the member of the event
	 */
	public void sendGuildMessage(TextChannel channel, String content, boolean willTag) {
	
		if (willTag) channel.sendMessage(memberTag + content).queue();
		else channel.sendMessage(content).queue();
	
	}
	
	/* Sends a private message to the specified user.
	 * 
	 * Parameters:
	 * user - User that will receive the message
	 * content - Message being sent
	 */
	public void sendPrivateMessage(User user, String content) {
		
		user.openPrivateChannel().queue((channel) -> {
			
			channel.sendMessage(content).queue();
			
		});
		
	}
		
}
