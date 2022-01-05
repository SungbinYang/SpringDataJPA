package me.sungbin.mismatch.granularity;

import java.util.List;
import java.util.Objects;

public class Account {

    private Long id;

    private Address address;

    private List<Study> studies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(address, account.address) && Objects.equals(studies, account.studies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, studies);
    }
}
