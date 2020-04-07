package pumpkin.darkest_dawn.core;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pumpkin.darkest_dawn.core.tasks.ChatFilter;
import pumpkin.darkest_dawn.core.tasks.Command;
import pumpkin.darkest_dawn.core.tasks.Verify;
import pumpkin.darkest_dawn.core.utils.BotFile;
import pumpkin.darkest_dawn.core.utils.Response;
import pumpkin.darkest_dawn.core.utils.Tool;

/* TODO) Steps before completion (Perform a thorough test over each part to ensure all works.)
 * DONE) Registration and Profile creation.
 * DONE) ChatFilter class and some BotFile changes
 * DONE) Setup the init() method
 * 4) Command class (adjust things to be as user-friendly as possible)
 * 5) Add necessary Roles and TextChannels
 * 6) Perform an all-around adjustment to the code
 * 7) Comment up the place
 * 8) One final review over everything
 * 9) Rejoice
 */

// Reacts to certain events that the bot listens to.
public class Listener extends ListenerAdapter {
	
	/* JDA Default Entities */
	private JDA jda; // Core of all entities.
	private Guild guild; // A Server hosted by Discord.
	private Category category; // Organizes a Guild's channels in a folder-fashion.
	private TextChannel channel; // Default channel the event was fired from.
	private Message message; // Text Message sent to Discord.
	private User user; // Discord account.
	private Member member; // Guild-specific User.
	
	/* JDA TextChannels */
	public TextChannel generalChannel; // #general
	public TextChannel beginnersguideChannel; // #beginners-guide
	public TextChannel modlogChannel; // #mod-log
	
	/* JDA Roles */
	public Role unverifiedRole; // @Unverified
	public Role characterlessRole; // @Characterless
	public Role playerRole; // @Player
	
	/* Java Utils */
	private PrintWriter writer; // Creates or overwrites a file.
	private Scanner scanner; // Reads the contents of a file.
	private Timer timer; // Delays the contained code block for a specified amount.

	/* Custom Utils */
	private Verify verify; // Verification system for the bot. Uses human logic.
	private BotFile botFile; // Reads and interacts with files from the bot.
	private Tool tool; // List of useful tools the bot uses for menial tasks.
	private Response response; // Contains all bot responses.
	private ChatFilter filter; // Filters out and deletes specific messages sent to a Guild.
	private Command command; // Interprets a given command and performs the action requested.
	
	/* Other */
	private String memberTag; // Tag of the user to indicate who the bot is responding to.
	private String content; // Raw message sent by the user
	private String userId; // ID of the user
	private String botMessage; // Response the bot will return
	private String categoryName; // Name of the category;
	private String channelName; // Name of the channel
	
	
	
		/* JDA EVENTS */
	
	@Override
	public void onReady(ReadyEvent event) {
		
		response = new Response();
		tool = new Tool();
		verify = new Verify();
		timer = new Timer("Listener");
		guild = event.getJDA().getGuildById(555972965955665929L);
		unverifiedRole = guild.getRoleById(613983789093355520L);
		characterlessRole = guild.getRoleById(555977370629177384L); 
		playerRole = guild.getRoleById(679567617610088488L);
		generalChannel = guild.getTextChannelById(614011250694815753L);
		beginnersguideChannel = guild.getTextChannelById(555977448639037441L);
		modlogChannel = guild.getTextChannelById(614750983498760193L);
		
	}
	
	@Override
	public void onReconnect(ReconnectedEvent event) {
		
		guild = event.getJDA().getGuildById(555972965955665929L);
		unverifiedRole = guild.getRoleById(613983789093355520L);
		characterlessRole = guild.getRoleById(555977370629177384L); 
		playerRole = guild.getRoleById(679567617610088488L);
		generalChannel = guild.getTextChannelById(614011250694815753L);
		beginnersguideChannel = guild.getTextChannelById(555977448639037441L);
		modlogChannel = guild.getTextChannelById(614750983498760193L);
		
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

		user = event.getUser();
		
		if (!user.isBot()) {

			member = event.getMember();
			userId = user.getId();
			new BotFile("profiles/player/" + userId + ".verify").create("Question: " + verify.getQuestion() + "\r\nAnswer: " + verify.getAnswer());
			guild.addRoleToMember(member, unverifiedRole).queue();
			sendPrivateMessage(user, response.getVerification() + verify.getQuestion());
			
		}
		
	}
	
	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		
		botFile = new BotFile("profiles/player/" + user.getId() + ".verify");
		
		if (unverifiedRole.getId().equals(event.getRoles().get(0).getId()) && botFile.exists()) {

			user = event.getUser();
			member = event.getMember();
			botFile.delete();
			new BotFile("profiles/player/registered/" + userId + ".profile").create(response.getPrefs());
			guild.removeRoleFromMember(member, guild.getRoleById(613983789093355520l)).queue();
			guild.addRoleToMember(member, characterlessRole).queue();
			guild.addRoleToMember(member, playerRole).queue();
			sendPrivateMessage(user, response.getVerifySuccess());
			sendGuildMessage(generalChannel, response.getNewMember(member.getAsMention()));
			
		}
		
	}
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
		user = event.getAuthor();
		userId = user.getId();
		botFile = new BotFile("profiles/player/" + userId + ".verify");
		content = event.getMessage().getContentRaw();
		
		if (botFile.exists()) {
			
			if (verify.check(content, botFile.find("answer"))) {

				botFile.delete();
				new BotFile("profiles/player/registered/" + userId + ".profile").create(response.getPrefs());
				guild.removeRoleFromMember(member, guild.getRoleById(613983789093355520l)).queue();
				sendPrivateMessage(user, response.getVerifySuccess());
				sendGuildMessage(generalChannel, response.getNewMember(member.getAsMention()));
				
			} else sendPrivateMessage(user, response.getVerifyFailure());
			
		}
		
	}
	
	/* Fires when a guild receives a message.
	 * 
	 * Parameters:
	 * event - class containing the information pertaining to this event
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		user = event.getAuthor();
		
		if (!user.isBot()) {

			channel = event.getChannel();
			message = event.getMessage();
			content = message.getContentRaw();
			
			if (new ChatFilter(content).check() && !channelName.startsWith("•")) { // If the message contained a blacklisted word/phrase wi.

				message.delete().queue();
				sendGuildMessage(modlogChannel, response.getBlockedMessage(channel.getAsMention(), member.getAsMention(), content));
				
			} else if (content.startsWith("$") && channel.getName().equals("bot-commands")) {
				
				command = new Command(user, message);
				
				if (command.isPrivateMsg()) for (String index : command.getResponse()) sendPrivateMessage(user, index);
				else for (String index : command.getResponse()) sendGuildMessage(index);
				
			}
							
		}
		
	}
	
	
	
		/* - CUSTOM METHODS - */
	
	/* Sends a message to the default channel.
	 * 
	 * Parameters:
	 * content - Message being sent
	 */
	public void sendGuildMessage(String content) {
		
		channel.sendMessage(content).queue();
		
	}
	
	/* Sends a message to the specified channel.
	 * 
	 * Parameters:
	 * channel - Channel that the message will be sent to
	 * content - Message being sent
	 */
	public void sendGuildMessage(TextChannel channel, String content) {
	
		channel.sendMessage(content).queue();
	
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
