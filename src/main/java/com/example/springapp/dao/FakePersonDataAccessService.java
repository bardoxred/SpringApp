package com.example.springapp.dao;

import com.example.springapp.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> personArrayList = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        personArrayList.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return personArrayList;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return personArrayList.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = getPersonById(id);
        if(person.isEmpty()){
            return 0;
        }
        personArrayList.remove(person.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return getPersonById(id).map(person1 -> {
            int indexOfPeron = personArrayList.indexOf(person1);
            if (indexOfPeron >= 0) {
                personArrayList.set(indexOfPeron, new Person(id,person.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}
