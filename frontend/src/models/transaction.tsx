import Asset from "./asset";

interface Transaction {
    asset: Asset,
    userId: number,
    value: number,
    direction: string // CREDIT, DEBIT
}

export default Transaction;