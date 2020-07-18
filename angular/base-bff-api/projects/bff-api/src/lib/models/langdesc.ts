// From BaseUtil - LangDesc
export class LangDesc {
  /** Bangladesh */
  bd?: string;
  /** English */
  en?: string;
  /** Indonesian */
  in?: string;
  /** Malaysia */
  my?: string;

  constructor(args?: {
      en?: string,
      bd?: string,
      in?: string,
      my?: string
    }) {

    if (args) {
      if (args.en) {
        this.en = args.en;
      }
      if (args.bd) {
        this.bd = args.bd;
      }
      if (args.in) {
        this.in = args.in;
      }
      if (args.my) {
        this.my = args.my;
      }
    }
  }

  setEn(desc: string) {
    this.en = desc;
  }

  setBd(desc: string) {
    this.bd = desc;
  }

  setIn(desc: string) {
    this.in = desc;
  }

  setMy(desc: string) {
    this.my = desc;
  }

  getLangDesc() {
    const langDesc = new LangDesc({en: ''});

    if (this.en) {
      langDesc.en = this.en;
    }
    if (this.bd) {
      langDesc.bd = this.bd;
    }
    if (this.in) {
      langDesc.in = this.in;
    }
    if (this.my) {
      langDesc.my = this.my;
    }
    return langDesc;
  }
}
