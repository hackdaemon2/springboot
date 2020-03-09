package com.lace.model;

import com.lace.constants.ApplicationConstants;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 *
 * @author centricgateway
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_password_reset_token")
@EqualsAndHashCode(callSuper = false)
public class PasswordResetToken extends AbstractEntity implements ApplicationConstants {

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION);
}
