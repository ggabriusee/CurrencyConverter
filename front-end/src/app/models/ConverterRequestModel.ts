import { ConverterFormModel } from "./ConverterFormModel";

export class ConverterRequestModel{
    
    amount: number;
    from: string;
    to: string;
    type: string;
    date: Date;

    constructor(cfm: ConverterFormModel){
        this.amount = +cfm.amount;
        this.from = cfm.from;
        this.to = cfm.to;
        this.type = cfm.type;
        this.date = new Date(cfm.date);
    }
}