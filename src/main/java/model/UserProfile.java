package model;

import net.datafaker.Faker;
import utility.Util;

import java.util.List;

public class UserProfile {

    String name;
    String email;
    String dob;
    String address;
    String password;
    String avatar;
    String testimonial;

    /**
     * To have the flexibility of user profiles without testimonial for other usages.
     * */
    public UserProfile() {
        Faker faker = new Faker();

        this.name = faker.name().fullName();
        this.email = faker.internet().safeEmailAddress(this.name);
        this.dob = faker.expression("#{date.birthday '18','70','yyyy-MM-dd'}");
        this.address = faker.address().fullAddress();
        this.password = faker.credentials().password(8, 12, true, false, true);
        this.avatar = Util.getAvatar();
    }

    public UserProfile(String testimonial) {
        this();
        this.testimonial = testimonial;
    }

    public UserProfile bindUserWithTestimonial(List<String> testimonials, int index) {
        // To avoid if index > list size
        return new UserProfile(testimonials.get(index % testimonials.size()));
    }
}


