package com.xangqun.springcloud.webflux.router


import com.xangqun.springcloud.webflux.handler.PersonHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route

@Configuration
open class RouterConfig {

    @Autowired lateinit var personHandler: PersonHandler

    @Bean
    open fun routerFunction(): RouterFunction<*> {
        return route(GET("/api/person").and(accept(APPLICATION_JSON)),
                HandlerFunction { personHandler.listPeople(it) })

            .and(route(GET("/api/person/{id}").and(accept(APPLICATION_JSON)),
                    HandlerFunction { personHandler.getPerson(it) }))
    }

}