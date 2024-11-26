package com.backend.autocarrerbridge.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_notification")
public class UserNotification extends AbstractAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        UserNotification that = (UserNotification) object;
        return Objects.equals(id, that.id)
                && Objects.equals(userAccount, that.userAccount)
                && Objects.equals(notification, that.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userAccount, notification);
    }
}
