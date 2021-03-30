import { AdType } from "./ad-type.enum";

export class Ad {
    id: number;
    adName: string;
    description: string;
    plotSurface: number;
    price: number;
    numberOfBedrooms: number;
    numberOfBathrooms: number;
    floorNumber: number;
    dateCreated: Date;
    lastUpdated: Date;
    isActive: boolean;
    adType: AdType;

}
