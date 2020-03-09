package com.lace.model;

import com.lace.constants.ApplicationConstants;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author centricgateway
 */
@Data
@Builder
@Entity
@Table(name = "tbl_user_verification_token")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVerificationToken extends AbstractEntity implements ApplicationConstants {

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION);
    
    private LocalDateTime calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return LocalDateTime.ofEpochSecond(cal.getTime().getTime(), 0, ZoneOffset.UTC);
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
