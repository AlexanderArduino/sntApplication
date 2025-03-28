package com.energetik.app.sntapplication.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gardener")
@NoArgsConstructor
@AllArgsConstructor
public class Gardener implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gardeners-roles",
            joinColumns = @JoinColumn(name = "gardener_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private String firstName; //имя
    private String middleName; //отчество
    private String lastName; //фамилия
    private String phone; // номер телефона

    @Column(unique = true)
    private String email; //почта
    private boolean isMember; //является ли членом СНТ
    private LocalDateTime dateEnter; // дата вступления
    private String documentEnter; // документ, подтверждающий вступление
    private LocalDateTime dateOut; // дата выхода
    private String reasonOut; // причина выхода

    @Column(unique = true)
    private String passport; // номер паспорта
    private String addressRegistration; //адрес регистрации
    private String addressResidential; // адрес проживания
    private String note; // заметка
    private boolean isArchive; // архивная ли запись

    @OneToMany(mappedBy = "gardener", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public LocalDateTime getDateEnter() {
        return dateEnter;
    }

    public void setDateEnter(LocalDateTime dateEnter) {
        this.dateEnter = dateEnter;
    }

    public String getDocumentEnter() {
        return documentEnter;
    }

    public void setDocumentEnter(String documentEnter) {
        this.documentEnter = documentEnter;
    }

    public LocalDateTime getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDateTime dateOut) {
        this.dateOut = dateOut;
    }

    public String getReasonOut() {
        return reasonOut;
    }

    public void setReasonOut(String reasonOut) {
        this.reasonOut = reasonOut;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddressRegistration() {
        return addressRegistration;
    }

    public void setAddressRegistration(String addressRegistration) {
        this.addressRegistration = addressRegistration;
    }

    public String getAddressResidential() {
        return addressResidential;
    }

    public void setAddressResidential(String addressResidential) {
        this.addressResidential = addressResidential;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
