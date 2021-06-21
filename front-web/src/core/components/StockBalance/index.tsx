import { StockBalance } from 'core/types/CostCalculation';
import './styles.scss';

type Props = {
    stockBalance: StockBalance;
}

const StockBalanceCard = ({ stockBalance }: Props) => {
    return (
        <div className="stockBalance-main">
            <div className="stockBalance-name">
                <h6>{stockBalance.name}</h6>
            </div>
            <div className="stockBalance-balance">
                <h6>{`${stockBalance.balance} ${ stockBalance.unity}`}</h6>
            </div>
            <div className="stockBalance-cost">
                <h6>{stockBalance.averageCost}</h6>
            </div>

        </div>
    );
}
export default StockBalanceCard;