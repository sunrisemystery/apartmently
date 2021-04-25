
import { City } from "./city";
import { Country } from "./country";

export class Address {
    streetName: string;
    streetNumber: number;
    postalCode: string;
    latitude: number;
    longitude: number;
    country: Country;
    city: City = new City();
}
