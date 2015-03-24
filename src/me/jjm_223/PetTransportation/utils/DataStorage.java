package me.jjm_223.PetTransportation.utils;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Wolf;

import java.util.UUID;

/**
 * Class for saving data to a config file.
 */
public class DataStorage {

    private FileConfiguration config;

    public DataStorage(FileConfiguration config) {
        //Sets config for this instance
        this.config = config;
    }

    //Returns the configuration above. Eh, I might need it.
    FileConfiguration getConfig() {
        return config;
    }

    public void savePet(Entity entity, UUID uuid) throws Exception {
        //Saves entity, throws an InvalidArgumentException when the entity specified is not an Ocelot or a Wolf.
        if (entity instanceof Ocelot || entity instanceof Wolf) {
            //If it is a cat, go to saveCat(), otherwise it must be a dog, so go to saveDog().
            if (entity instanceof Ocelot) {
                Ocelot pet = (Ocelot) entity;
                saveCat(pet, uuid);
            } else {
                Wolf pet = (Wolf) entity;
                saveDog(pet, uuid);
            }
        } else {
            throw new Exception("The entity specified was neither a wolf, nor was it an ocelot.");
        }
    }

    //Saves a wolf entity
    private void saveDog(Wolf wolf, UUID uuid) {
        //Convert UUID to string for storage.
        String uuidString = uuid.toString();

        //Store important dog info in variables.
        String petName = wolf.getCustomName();
        DyeColor collarColor = wolf.getCollarColor();
        int colorRGB = collarColor.getColor().asRGB();
        String petOwnerUUID = wolf.getOwner().getUniqueId().toString();
        boolean isSitting = wolf.isSitting();
        int age = wolf.getAge();
        double wolfHealth = wolf.getHealth();

        //Save dog info.
        config.set("pets." + uuidString + ".petName", petName);
        config.set("pets." + uuidString + ".collarColor", colorRGB);
        config.set("pets." + uuidString + ".petOwner", petOwnerUUID);
        config.set("pets." + uuidString + ".isSitting", isSitting);
        config.set("pets." + uuidString + ".age", age);
        config.set("pets." + uuidString + ".health", wolfHealth);

    }

    private void saveCat(Ocelot ocelot, UUID uuid) {
        //Convert UUID to string for storage.
        String uuidString = uuid.toString();

        //Store important cat info in variables.
        String petName = ocelot.getCustomName();
        Ocelot.Type breed = ocelot.getCatType();
        String breedString = breed.toString();
        String petOwnerUUID = ocelot.getOwner().getUniqueId().toString();
        boolean isSitting = ocelot.isSitting();
        int age = ocelot.getAge();
        double catHealth = ocelot.getHealth();

        //Save cat info.
        config.set("pets." + uuidString + ".petName", petName);
        config.set("pets." + uuidString + ".breed", breedString);
        config.set("pets." + uuidString + ".petOwner", petOwnerUUID);
        config.set("pets." + uuidString + ".isSitting", isSitting);
        config.set("pets." + uuidString + ".age", age);
        config.set("pets." + uuidString + ".health", catHealth);
    }

    public void restorePet(Entity entity, UUID uuid) throws Exception {
        //Make sure entity is an Ocelot or a Wolf, if it isn't, throw an InvalidArgumentException.
        if (entity instanceof Ocelot || entity instanceof Wolf) {
            //If entity is a cat, then pass it on to restoreCat(), otherwise it must be a dog, so pass it on to restoreDog().
            if (entity instanceof Ocelot) {
                Ocelot pet = (Ocelot) entity;
                restoreCat(pet, uuid);
            } else {
                Wolf pet = (Wolf) entity;
                restoreDog(pet, uuid);
            }
        } else {
            throw new Exception("The entity specified was neither a wolf, nor was it an ocelot.");
        }
    }

    private void restoreDog(Wolf wolf, UUID uuid) {
        //Convert UUID to string for loading from config.
        String uuidString = uuid.toString();

        //Get pet data from config.
        String petName = config.getString("pets." + uuidString + ".petName");
        int colorRGB = config.getInt("pets." + uuidString + ".collarColor");
        UUID petOwnerUUID = UUID.fromString(config.getString("pets." + uuidString + ".petOwner"));
        boolean isSitting = config.getBoolean("pets." + uuidString + ".isSitting");
        int age = config.getInt("pets." + uuidString + ".age");
        double wolfHealth = config.getDouble("pets." + uuidString + ".health");

        //Sets pet data.
        wolf.setCustomName(petName);
        wolf.setCollarColor(DyeColor.getByColor(Color.fromRGB(colorRGB)));
        wolf.setOwner(Bukkit.getOfflinePlayer(petOwnerUUID));
        wolf.setSitting(isSitting);
        wolf.setAge(age);
        wolf.setHealth(wolfHealth);
    }

    private void restoreCat(Ocelot ocelot, UUID uuid) {
        //Convert UUID to a string for reading the config.
        String uuidString = uuid.toString();

        //Get values from config.
        String petName = config.getString("pets." + uuidString + ".petName");
        String breedString = config.getString("pets."+ uuidString + ".breed");
        Ocelot.Type breed = Ocelot.Type.valueOf(breedString);
        UUID petOwnerUUID = UUID.fromString(config.getString("pets." + uuidString + ".petOwner"));
        boolean isSitting = config.getBoolean("pets." + uuidString + ".isSitting");
        int age = config.getInt("pets." + uuidString + ".age");
        double catHealth = config.getDouble("pets." + uuidString + ".health");

        //Set cat info.
        ocelot.setCustomName(petName);
        ocelot.setCatType(breed);
        ocelot.setOwner(Bukkit.getOfflinePlayer(petOwnerUUID));
        ocelot.setSitting(isSitting);
        ocelot.setAge(age);
        ocelot.setHealth(catHealth);
    }
}