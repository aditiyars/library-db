package com.exercise.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class User {
    private int id;
    private String name;
    
    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("\nid : %s \nname : %s\n", this.id, this.name);
    }

        
}
