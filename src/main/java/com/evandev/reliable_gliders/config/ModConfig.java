package com.evandev.reliable_gliders.config;

import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve("reliable_gliders.json").toFile();
    private static ModConfig INSTANCE;

    @SerializedName("updraft_strength")
    public double updraftStrength = 0.7;

    @SerializedName("updraft_height")
    public int updraftHeight = 15;

    @SerializedName("updraft_disabled_dimensions")
    public List<String> updraftDisabledDimensions = new ArrayList<>(List.of("minecraft:the_nether"));

    @SerializedName("horizontal_speed")
    public double horizontalSpeed = 1.0;

    @SerializedName("equip_to_chestplate")
    public boolean equipToChestplate = false;

    @SerializedName("disable_in_creative")
    public boolean disableInCreative = false;

    public static ModConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
                if (INSTANCE == null) {
                    INSTANCE = new ModConfig();
                }
                if (INSTANCE.updraftDisabledDimensions == null) {
                    INSTANCE.updraftDisabledDimensions = new ArrayList<>();
                }
            } catch (Exception e) {
                Constants.LOG.error("Failed to load reliable_gliders.json", e);
                INSTANCE = new ModConfig();
            }
            save();
        } else {
            INSTANCE = new ModConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            Constants.LOG.error("Failed to save reliable_gliders.json", e);
        }
    }
}