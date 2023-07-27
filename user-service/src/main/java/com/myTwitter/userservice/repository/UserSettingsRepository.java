package com.myTwitter.userservice.repository;

import com.myTwitter.userservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM User user WHERE user.email = :email")
    boolean isEmailExist(@Param("email") String email);

    @Modifying
    @Query("UPDATE User user SET user.email = :email WHERE user.id = :userId")
    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.username = :username WHERE user.id = :userId")
    void updateUsername(@Param("username") String username, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.countryCode = :countryCode, user.phone = :phone WHERE user.id = :userId")
    void updatePhone(@Param("countryCode") String countryCode, @Param("phone") Long phone, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.country = :country WHERE user.id = :userId")
    void updateCountry(@Param("country") String country, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.gender = :gender WHERE user.id = :userId")
    void updateGender(@Param("gender") String gender, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.language = :language WHERE user.id = :userId")
    void updateLanguage(@Param("language") String language, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.mutedDirectMessages = :mutedDirectMessages WHERE user.id = :userId")
    void updateDirectMessageRequests(@Param("mutedDirectMessages") boolean mutedDirectMessages, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.privateProfile = :privateProfile WHERE user.id = :userId")
    void updatePrivateProfile(@Param("privateProfile") boolean privateProfile, @Param("userId") Long userId);


}
