package com.allangomes.micronaut.model

import io.micronaut.core.annotation.Introspected

@Introspected
open class Greeting(val msg: String = "")
