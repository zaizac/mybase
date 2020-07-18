import { State } from './state';

export class City {
    cityId?: number;
    cityCd?: string;
    cityDesc?: string;
    state?: State;

    constructor(args: {
        cityId?: number,
        cityCd?: string,
        cityDesc?: string,
        state?: State
    }) {
        this.cityId = args.cityId;
        this.cityCd = args.cityCd;
        this.cityDesc = args.cityDesc;
        this.state = args.state;
    }
}
