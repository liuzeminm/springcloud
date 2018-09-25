package com.xangqun.springcloud.webflux.service

import com.xangqun.springcloud.webflux.dao.PersonRepository
import com.xangqun.springcloud.webflux.model.Person
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService : PersonRepository {
    var persons: MutableMap<Int, Person> =hashMapOf()

    constructor() {
        this.persons[1] = Person("Jack", 20)
        this.persons[2] = Person("Rose", 16)
    }


    override fun getPerson(id: Int): Mono<Person> {
        return Mono.justOrEmpty(this.persons[id])
    }

    override fun allPeople(): Flux<Person> {
        return Flux.fromIterable(this.persons.values)
    }

    override fun savePerson(person: Mono<Person>): Mono<Void> {
        return person.doOnNext {
            val id = this.persons.size + 1
            persons.put(id, it)
            println("Saved ${person} with ${id}")
        }.thenEmpty(Mono.empty())

    }
}