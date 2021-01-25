import Position from "./position";
import Transaction from "./transaction";

interface IUser {
    name: string,
    balance: number,
    assets: Array<Position>,
    transactions: Array<Transaction>
}

class User implements IUser{
    name: string;
    balance: number;
    assets: Position[];
    transactions: Transaction[];

    constructor(user: IUser){
        this.name = user.name;
        this.balance = user.balance;
        this.assets = user.assets;
        this.transactions = user.transactions;
    }
}

export default User;