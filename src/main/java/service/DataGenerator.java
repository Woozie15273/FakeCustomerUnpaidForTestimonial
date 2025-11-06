package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import model.Testimonial;
import model.UserProfile;
import utility.LoggerUtil;
import utility.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataGenerator {

    Testimonial text = new Testimonial();
    UserProfile user = new UserProfile();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Integer userCount = Util.getUserCount();
    private final List<String> testimonials = text.getTestimonialAsList();
    private final String path = Util.getOutputPath();

    /**
     * Centralize method to write results to a file (or HTTP response for future-proof)
     * */
    private void exportUsers(OutputStream outputStream) {
        try (JsonWriter writer = new JsonWriter(
                new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            writeUsers(writer, userCount, testimonials);

        } catch (IOException e) {
            LoggerUtil.error("Failed to export users: ", e);
        }
    }

    private void writeUsers(JsonWriter writer, int userCount, List<String> testimonials) throws IOException {
        writer.setIndent("  "); // pretty-print

        writer.beginArray();
        for (int i = 0; i < userCount; i++) {
            UserProfile profile = user.bindUserWithTestimonial(testimonials, i);
            gson.toJson(profile, UserProfile.class, writer);
        }
        writer.endArray();
    }

    /**
     * Creates the file if it doesn't exist, overwrites if it does.
     */
    public void exportToJson(String fileName) {
        File file = new File(path, fileName);
        try (OutputStream os = new FileOutputStream(file, false)) { // false = overwrite mode
            exportUsers(os);
            LoggerUtil.info("Export successful: " + file.getAbsolutePath());
        } catch (IOException e) {
            LoggerUtil.error("Failed to export JSON output: ", e);
        }
    }

    public void exportToJson(){
        exportToJson("users.json");
    }


}