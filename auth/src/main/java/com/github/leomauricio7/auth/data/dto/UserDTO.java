package com.github.leomauricio7.auth.data.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -2919719583811632976L;

    private String userName;
    private String password;

}
