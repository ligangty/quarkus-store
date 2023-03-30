package foo.bar.store.util;

import javax.enterprise.inject.Produces;

import foo.bar.store.util.Vat;

import javax.inject.Named;

import foo.bar.store.util.Discount;

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