package com.example.hcpm_be.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Lob
//    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
//    @NotBlank
//    @Size(min = 3, max = 40)
    private String username;
//    @NotBlank
//    @Size(min = 3, max = 40)
    private String fullName;

//    @Pattern(regexp = "^\\d{10}$", message = "điền đúng số điện thoại")
    private String phone;

//    @NaturalId
//    @NotBlank
//    @Size(max = 50)
//    @Email
    private String email;

    private String address;

    private String category;

    private Long age;

    private String brand;           //nhãn hiệu

    private String national;        //quốc tịch

    private String workspace;      //nơi làm việc

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private Long experience = 0L;  //số kinh nghiệm-tháng
//    @JsonIgnore
//    @NotBlank
//    @Size(min = 3, max = 40)
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(Set<Role> roles) {
        this.roles = roles;
    }

//    public User(@NotBlank @Size(min = 3, max = 40) String fullName,
//                @NotBlank @Size(max = 50) @Email String email,
//                @NotBlank @Size(min = 3, max = 40) String username,
//                @NotBlank @Size(min = 3, max = 40) String password) {
//        this.fullName = fullName;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//    }
    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public User(Long id, String avatar, String username, String fullName,String phone, String email, String password, Set<Role> roles){
        this.id=id;
        this.avatar=avatar;
        this.username=username;
        this.fullName=fullName;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.roles=roles;
    }
}
