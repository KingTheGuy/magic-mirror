package com.surv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.surv.items.Item_Manager;

// import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

//DOING//
//TODO: save last death to file and have it load back up on restart

/////////

//SOME TIME//
//TODO: clean/ do re-something everything
//TODO: change the way the player recives a magic mirror 
//maybe some in world recipe
//TODO: figure out how to "add" my own item. so that the magic mirror is stricktly made
//TODO: implement save/ reload data.

//TODO: i could possibly add in the TP to other players again..

//TODO: add a confirmation menu or sub menu.. not sure
//will be needed for reseting home tp or teleporting somewhere.

//TODO: move SPAWN, SHOPS.. and any other server wide warps to its own menu

//TODO: reset/increase the blindness effect when the player selectes a menu option

//FIXME: let player know not to have a shield in hand.. 
//or add code to ignore the shield or any other item in offhand

public class magic_mirror implements Listener {
  ///// Config////
  // String TheItemName = "Magic Mirror";
  // Material TheItemType = Material.BOOK;
  //// END////

  // TODO: add a delay, on item use.. will cause some problems otherwise.
  // may need to change the confirmation on select to be crouch.
  // WHAT AM I DOING WRONG HERE?//
  public List<player_deaths> deaths = new ArrayList<>();

  class player_deaths {
    public Player name;
    public Location loc;

  }

  public String PLAYER_WARPS = "player_warps.yaml";

  // public void saveToFile() {
  // StringBuilder sb = new StringBuilder();
  // for (Camp camp : campfires) {
  // sb.append("- dimensionName: ").append(camp.dimensionName).append("\n")
  // .append(" posX: ").append(camp.posX).append("\n")
  // .append(" posY: ").append(camp.posY).append("\n")
  // .append(" posZ: ").append(camp.posZ).append("\n")
  // .append(" owner: ").append(camp.owner).append("\n")
  // .append(" pick_crops: ").append(camp.pick_crops).append("\n")
  // .append(" live_stock: ").append(camp.live_stock).append("\n\n");
  // }
  // String yamlString = sb.toString();
  // try (FileWriter writer = new FileWriter(PLAYER_WARPS)) {
  // writer.write(yamlString);
  // } catch (IOException o) {
  // }
  // }

  // public void loadFromFile() {
  // try {
  // File file = new File(PLAYER_WARPS);
  // Scanner scanner = new Scanner(file);
  // Camp new_claim = new Camp();
  // while (scanner.hasNextLine()) {
  // String line = scanner.nextLine();
  // String[] splits = line.split(": ", 0);
  // if (line.toString().startsWith("-")) {
  // System.out.println("::this is the START of a claim::");
  // }
  // if (splits[0].contains("dimensionName")) {
  // new_claim.dimensionName = splits[1];
  // System.out.println(String.format("size of name WORLD is: %s should be 5",
  // splits[1].length()));
  // } else if (splits[0].contains("posX")) {
  // new_claim.posX = Integer.parseInt(splits[1]);

  // } else if (splits[0].contains("posY")) {
  // new_claim.posY = Integer.parseInt(splits[1]);

  // } else if (splits[0].contains("posZ")) {
  // new_claim.posZ = Integer.parseInt(splits[1]);

  // } else if (splits[0].contains("owner")) {
  // new_claim.owner = splits[1];

  // } else if (splits[0].contains("pick_crops")) {
  // new_claim.pick_crops = Boolean.parseBoolean(splits[1]);

  // } else if (splits[0].contains("live_stock")) {
  // new_claim.live_stock = Boolean.parseBoolean(splits[1]);
  // } else if (line.length() == 0) {
  // campfires.add(new_claim);
  // System.out.println(new_claim.toString());
  // System.out.println("::this is the END of a claim::");
  // }
  // System.out.println(line);
  // }
  // scanner.close();
  // } catch (FileNotFoundException e) {
  // System.out.println("File not found");
  // }
  // }
  // HUH//

  @EventHandler
  public void onServerStart(ServerLoadEvent ev) {
    System.out.println(String.format("Does this not work %s", deaths.toString()));
    // loadFromFile();
    System.out.println(String.format("Does this not work %s", deaths.toString()));
  }

  // HUH//

  class PlayerMenuOption {
    String playerName;
    String selection = "-";
  }

  menu new_menu = new menu();

  // ArrayList<PlayerMenuOption> playersWithMenuOpen = new
  // ArrayList<PlayerMenuOption>();

  // public String menuSelecting(float pitch, Player player) {
  // String selected = "-";
  // if (pitch >= -90 && pitch <= -60) {
  // selected = "BED";
  // }
  // if (pitch >= -61 && pitch <= -30) {
  // selected = "SPAWN";
  // }
  // if (pitch >= -29 && pitch <= 29) {
  // selected = "LAST DEATH";
  // }
  // if (pitch >= 30 && pitch <= 61) {
  // selected = "SHOPS";
  // }
  // if (pitch >= 60 && pitch <= 90) {
  // selected = "INFO";
  // }
  // // play sound as the player changes their selection
  // // index index = playersWithMenuOpen
  // // .indexOf(playersWithMenuOpen.stream().filter(o -> o.playerName ==
  // // player.getName()).findFirst().orElse(-1));
  // boolean there = playersWithMenuOpen.stream().filter(o -> o.playerName ==
  // player.getName()).findFirst().isPresent();
  // int index = -1;
  // if (there == true) {
  // index = playersWithMenuOpen
  // .indexOf(playersWithMenuOpen.stream().filter(o -> o.playerName ==
  // player.getName()).findFirst().get());
  // }
  // // index being if the player has a spot in the array
  // if (index > -1) {
  // String oldSelection = playersWithMenuOpen.get(index).selection;
  // if (oldSelection != selected) {
  // player.playSound(player.getLocation(),
  // Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 1f, 1f);
  // }
  // playersWithMenuOpen.get(index).selection = selected;
  // }
  // return selected;
  // }

  @EventHandler
  public void onLeave(PlayerQuitEvent ev) {
    Player player = ev.getPlayer();
    new_menu.close_menu(player);
  }

  // @EventHandler
  // public void onChat(AsyncChatEvent ev) {
  // Player player = ev.getPlayer();
  // Audience audience = Audience.audience(player);
  // String msgContent =
  // PlainTextComponentSerializer.plainText().serialize(ev.originalMessage());
  // if (msgContent.startsWith("~")) {
  // if (msgContent.contains("head")) {

  // }
  // } else {

  // }
  // String msg = "[slimshady]<" + player.getName() + "> " + msgContent;
  // audience.sendMessage(() -> Component.text(ChatColor.WHITE + ":" + msg));
  // ev.setCancelled(true);

  // }

  // FIXME: Issue when player clicks blocks with the item.
  // NOTE: fixed this by changing click to crouch. to confirm selection
  @EventHandler
  public void onPlayerSneak(PlayerToggleSneakEvent ev) {
    Player player = ev.getPlayer();
    boolean isSneaking = ev.isSneaking();

    int index = new_menu.get_player(player);
    // boolean there = playersWithMenuOpen.stream().filter(o -> o.playerName ==
    // player.getName()).findFirst().isPresent();
    // int index = -1;
    // if (there == true) {
    // index = playersWithMenuOpen
    // .indexOf(playersWithMenuOpen.stream().filter(o -> o.playerName ==
    // player.getName()).findFirst().get());
    // }

    if (isSneaking == true && index > -1) {
      // String playerSelected = playersWithMenuOpen.get(index).selection;
      String playerSelected = new_menu.has_menu.get(index).selected;
      boolean success = false;
      int xp = player.getLevel();
      if (playerSelected == "BED") {
        if (xp >= 6) {
          var hasBed = player.getBedSpawnLocation();
          if (hasBed == null) {
            player.sendMessage(ChatColor.RED + "you don't have a bed.");
          } else {
            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            player.setLevel(xp - 6);
            player.teleportAsync(player.getBedSpawnLocation());
            success = true;
          }
        }
      }
      if (playerSelected == "SPAWN") {
        // 5xp
        if (xp >= 4) {
          // tp
          player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
          player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
          player.setLevel(xp - 4);
          player.teleportAsync(Bukkit.getWorld("world").getSpawnLocation());
          success = true;

        }

      }
      if (playerSelected == "SHOPS") {
        // 5xp
        if (xp >= 3) {
          // tp
          player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
          player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
          player.setLevel(xp - 3);
          Location new_location = new Location(Bukkit.getWorld("world"), -1806, 73, 1502);
          // player.teleportAsync(Bukkit.getWorld("world").getSpawnLocation());
          player.teleportAsync(new_location);
          success = true;

        }

      }
      if (playerSelected == "LAST DEATH") {
        // 3xp
        if (xp >= 3) {
          int dex = -1;
          if (deaths.size() > 0) {
            dex = deaths.indexOf(deaths.stream().filter(o -> o == player).findFirst().get());
            if (dex > -1) {
              // NOTE: this looks like its had been fixed, this line was using a random index,
              // but no more.
              player_deaths death_data = deaths.get(index);
              player.setLevel(xp - 3);
              player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
              player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
              player.teleportAsync(death_data.loc);
              success = true;

            }
          } else {
            player.sendMessage(ChatColor.RED + "no deaths");
          }
          if (dex == -1) {
            player.sendMessage(ChatColor.RED + "seems like you havent died yet");

          }
        }

      }
      if (playerSelected == "INFO") {
        // show the player info on how to use item
        player.sendMessage(
            ChatColor.AQUA + "What is there to tell? you crouch to carry on with your selection and it costs xp.");
        player.sendMessage(ChatColor.AQUA
            + "Oh btw if you have an empty bottle in hand you can crouch use it and collect you xp in it. Great for later use.");
        success = true;

      }
      if (playerSelected == "CLOSE") {
        success = true;

      }
      if (success == true) {
        // FIXME: there is an issue here, right clicking a block acts incorrectly
        // playersWithMenuOpen.remove(index);
        // player.removePotionEffect(PotionEffectType.BLINDNESS);
        new_menu.close_menu(player);
      } else {
        if (playerSelected != "-") {
          Audience audience = Audience.audience(player);
          audience.sendActionBar(() -> Component.text(ChatColor.RED + "not Enough xp"));
          player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1f, 1f);
          // playersWithMenuOpen.remove(index);
          // player.removePotionEffect(PotionEffectType.BLINDNESS);
          new_menu.close_menu(player);
        }
      }
      player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);

    }

  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent ev) {
    Player player = ev.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    Material itemType = item.getType();
    Action action = ev.getAction();

    // player.sendMessage(ChatColor.AQUA + "______");
    // player.sendMessage(ChatColor.AQUA + "ItemType: " + itemType);

    // TODO: min level of xp needed to store is 10 levels.. should also give back 10
    // levels
    // Bottle you xp
    Audience audience = Audience.audience(player);
    if (itemType.equals(Material.GLASS_BOTTLE)) {
      if (player.isSneaking()) {
        int xp = player.getLevel();
        if (xp >= 10) {
          player.setLevel(xp - 10);
          // item.setType(Material.EXPERIENCE_BOTTLE);
          player.getInventory().removeItem(new ItemStack(Material.GLASS_BOTTLE));
          player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE));
          player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_DRIP, 1f, 1f);
        } else {
          audience.sendActionBar(() -> Component.text(ChatColor.RED + "Need 10xp levels"));
          player.playSound(player.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1f, 1f);
        }
      }
    }
    if (itemType.equals(Material.EXPERIENCE_BOTTLE)) {
      if (player.isSneaking()) {
        int xp = player.getLevel();
        player.setLevel(xp + 10);
        player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
        player.getInventory().removeItem(new ItemStack(Material.EXPERIENCE_BOTTLE));
        player.playSound(player.getLocation(), Sound.AMBIENT_UNDERWATER_EXIT, 0.5f, 1f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1f);
        ev.setCancelled(true);

      }

    }
    // on use Magic Mirror
    if (ev.getItem() != null) {
      if (ev.getItem().equals(Item_Manager.mm)) {
        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
          new_menu.open_menu(player);
        } else {
          // close the menu
          new_menu.close_menu(player);
          player.sendMessage(
              ChatColor.GOLD + "HOW TO USE: look up/down to see all selections. to confirm your selection, crouch.");
          player.sendMessage(ChatColor.GRAY
              + "NOTE: you can crouch click with an empty bottle to store your xp for later use. crouch use an enchanted bottle to get the full xp back.");
          player.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1f, 1f);
          //// player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);

        }
        // }
      }
    }
  }

  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent ev) {
    if (ev.getEntity() instanceof Enderman && ev.getDamager() instanceof Player) {
      Player player = (Player) ev.getDamager();
      // player.sendMessage("Something is working ..but..");
      // Enderman enderman = (Enderman) ev.getEntity();
      if (player.getInventory().getItemInMainHand().getType().equals(Material.BOOK)) {
        // player.sendMessage("This should be working then..");
        player.getInventory().addItem(Item_Manager.mm);
        player.playSound(ev.getEntity().getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, SoundCategory.BLOCKS, 1, 1);

        ItemStack old_stack = player.getInventory().getItemInMainHand();
        old_stack.setAmount(old_stack.getAmount() - 1);
        player.getInventory().setItemInMainHand(old_stack);
      }
      // do something when player attacks enderman
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent ev) {
    var player = ev.getPlayer();
    var block = ev.getTo().clone().subtract(0.0, 0.1, 0.0).getBlock();
    // player.sendMessage("this is it:" + block.getType());
    // player.getVehicle().getType();

    // speed effect stuff
    // if (block.getType() == Material.DIRT_PATH) {
    // player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40,
    // 1).withAmbient(false).withParticles(true));
    // if (player.isInsideVehicle()) {
    // EntityType entType = player.getVehicle().getType();
    // if (entType == EntityType.HORSE || entType == EntityType.MULE || entType ==
    // EntityType.DONKEY
    // || entType == EntityType.PIG) {
    // if (player.getVehicle() instanceof LivingEntity livingEntity) {
    // livingEntity.addPotionEffect(
    // new PotionEffect(PotionEffectType.SPEED, 40,
    // 4).withAmbient(false).withParticles(true));
    // }
    // }
    // }
    // }

    // does anyone have the menu?
    // player.sendMessage(new_menu.has_menu.toString());

    // Magic Mirror stuff
    int index = new_menu.get_player(player);
    if (index != -1) {

      List<String> options = new ArrayList<>();
      options.add("BED");
      options.add("SPAWN");
      options.add("LAST DEATH");
      options.add("SHOPS");
      options.add("INFO");
      new_menu.menu_options(options, player);

      // TODO: this needs to be cleaned up. no need for all these strings
      Audience audience = Audience.audience(player);
      String selection = new_menu.has_menu.get(index).selected;
      if (selection == "BED") {
        audience.sendActionBar(() -> Component.text("BED" + ChatColor.GRAY + " 6xp"));
      } else if (selection == "SPAWN") {
        audience.sendActionBar(() -> Component.text("SPAWN" + ChatColor.GRAY + " 4xp"));

      } else if (selection == "LAST DEATH") {
        audience.sendActionBar(() -> Component.text("LAST DEATH" + ChatColor.GRAY + " 3xp"));

      } else if (selection == "SHOPS") {
        audience.sendActionBar(() -> Component.text("SHOPS" + ChatColor.GRAY + " 3xp"));

      } else {
        audience.sendActionBar(() -> Component.text(selection));
      }
      boolean hasBlindness = false;
      for (PotionEffect effect : player.getActivePotionEffects()) {
        if (effect.getType().equals(PotionEffectType.BLINDNESS)) {
          hasBlindness = true;
        }
      }
      if (hasBlindness == false) {
        new_menu.close_menu(player);
        // playersWithMenuOpen.remove(index);
      }

    }
  }

  ArrayList<String> hasItemInHand = new ArrayList<String>();

  @EventHandler
  public void onItemSwitch(PlayerItemHeldEvent ev) {
    Player player = ev.getPlayer();
    int newSlot = ev.getNewSlot();
    var isEmpty = player.getInventory().getItem(newSlot);
    boolean notHoldingItem = false;

    if (isEmpty != null) {
      Material itemType = isEmpty.getType();
      // String itemInUseName =
      // player.getInventory().getItem(newSlot).displayName().toString();

      // if (ev.getItem().equals(Item_Manager.mm)) {
      if (itemType.equals(Item_Manager.mm.getType())) {
        // if (itemInUseName.contains(TheItemName)) {
        if (!hasItemInHand.contains(player.getName())) {
          hasItemInHand.add(player.getName());
        }
        // }
      } else {
        notHoldingItem = true;
      }
    } else {
      notHoldingItem = true;
    }
    if (notHoldingItem == true) {
      if (hasItemInHand.size() > 0) {
        if (hasItemInHand.contains(player.getName())) {
          hasItemInHand.remove(player.getName());
          new_menu.close_menu(player);
        }
      }
    }

  }

  class PlayerDeathLoc {
    String playerName;
    String dim;
    int x;
    int y;
    int z;

  }

  // ArrayList<PlayerDeathLoc> playerDeathData = new ArrayList<PlayerDeathLoc>();

  @EventHandler
  public void onPlayerDeath(EntityDeathEvent ev) {
    EntityType entity = ev.getEntityType();
    // check if its a player that died
    if (entity.equals(EntityType.PLAYER)) {
      Entity player = ev.getEntity();
      var location = player.getLocation();
      // check if the array contains the player or not
      // boolean there = playerDeathData.stream().filter(o -> o.playerName ==
      // player.getName()).findFirst()
      // .isPresent();
      // if (there == true) {
      // int index = playerDeathData
      // .indexOf(playerDeathData.stream().filter(o -> o.playerName ==
      // player.getName()).findAny().get());
      // playerDeathData.get(index).dim = player.getWorld().getName();
      // playerDeathData.get(index).x = (int) location.getX();
      // playerDeathData.get(index).y = (int) location.getY();
      // playerDeathData.get(index).z = (int) location.getZ();
      // } else {
      // PlayerDeathLoc playerDeath = new PlayerDeathLoc();
      // playerDeath.playerName = player.getName();
      // playerDeath.dim = player.getWorld().getName();
      // playerDeath.x = (int) location.getX();
      // playerDeath.y = (int) location.getY();
      // playerDeath.z = (int) location.getZ();
      // playerDeathData.add(playerDeath);

      // }
      player_deaths this_death = new player_deaths();
      this_death.name = (Player) player;
      this_death.loc = player.getLocation();
      int index = -1;
      if (deaths.size() > 0) {
        index = deaths.indexOf(deaths.stream().filter(p -> p == player).findFirst().get());
        if (index > -1) {
          deaths.get(index).loc = player.getLocation();
        }
      }
      if (index == -1) {
        deaths.add(this_death);
      }
      // saveToFile();
    }
  }

}
