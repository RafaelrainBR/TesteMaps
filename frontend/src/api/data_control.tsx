import User from '../models/user';
import api from './api';

async function getUser(id: number){
    const response = await api.get(`/users/${id}`);
    const data = response.data
    console.log(data)

    return new User({
        name: data.name,
        balance: data.balance,
        assets: [
            {
                asset: {
                    name: "asset1",
                    type: "RF",
                    marketPrice: 350,
                },
                amount: 300,
                marketPrice: 250,
                income: 200,
                gain: 250
            }
        ],
        transactions: data.transactions
    });
}

export default { getUser };