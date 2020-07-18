import { Country } from './country';

export class State {

    stateId?: number;
    stateCd?: string;
    stateDesc?: string;
    country?: Country;

    constructor(args: {
        stateId?: number,
        stateCd?: string,
        stateDesc?: string,
        country?: Country
    }) {
        this.stateId = args.stateId;
        this.stateCd = args.stateCd;
        this.stateDesc = args.stateDesc;
        this.country = args.country;
    }
}
