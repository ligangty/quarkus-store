package demo.quarkus.store.util;

import javax.enterprise.inject.Produces;

import javax.inject.Named;

public class NumberProducer
{

    @Produces
    @Vat
    @Named
    Float vatRate;

    @Produces
    @Discount
    @Named
    Float discountRate;
}