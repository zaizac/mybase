export class Country {
    cntryCd?: string;
    cntryDesc?: string;
    ntnltyDesc?: string;
    cntryInd?: boolean;
    intCallMode?: string;
    crncyCode?: string;
    mcProfTypeMtdtId?: number;

    constructor(args: {
        cntryCd?: string,
        cntryDesc?: string,
        ntnltyDesc?: string,
        cntryInd?: boolean,
        intCallMode?: string,
        crncyCode?: string,
        mcProfTypeMtdtId?: number
    }) {
        this.cntryCd = args.cntryCd;
        this.cntryDesc = args.cntryDesc;
        this.ntnltyDesc = args.ntnltyDesc;
        this.cntryInd = args.cntryInd;
        this.intCallMode = args.intCallMode;
        this.crncyCode = args.crncyCode;
        this.mcProfTypeMtdtId = args.mcProfTypeMtdtId;
    }
}
