
import { Address } from "./address";
import { User } from "./user";


export class AdOffer {
    adName: string;
    description: string;
    plotSurface: number;
    price: number;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    floorNumber: number;
    address: Address = new Address();
    user: User = new User();
    // adImages: Array<AdImage> =  new Array();
    adType: string;
}
