package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utility.LoggerUtil;
import utility.Util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class Testimonial {

    private final List<TestimonialEntry> testimonials;

    // Inner class to match JSON structure
    private static class TestimonialEntry {
        int order;
        String text;
    }

    public Testimonial() {
        this.testimonials = loadTestimonials();
    }

    private List<TestimonialEntry> loadTestimonials() {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(Util.loadFromResource("Testimonial.json")))) {
            return parseTestimonials(reader);
        } catch (Exception e) {
            LoggerUtil.error("Returning empty list due to: ", e);
            return List.of(); // empty list fallback
        }
    }

    /**
     * Parses a Reader into a list of TestimonialEntry objects.
     */
    private List<TestimonialEntry> parseTestimonials(Reader reader) {
        Type listType = new TypeToken<List<TestimonialEntry>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }

    public List<String> getTestimonialAsList() {
        return testimonials.stream()
                .map(entry -> entry.text)
                .toList();
    }

}
