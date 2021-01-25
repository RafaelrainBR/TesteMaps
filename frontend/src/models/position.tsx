import Asset from "./asset";

interface Position {
    asset: Asset,
    amount: number,
    marketPrice: number,
    income: number,
    gain: number
}

export default Position;