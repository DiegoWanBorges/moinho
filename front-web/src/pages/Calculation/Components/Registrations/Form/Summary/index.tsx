import { CostCalculation } from 'core/types/CostCalculation';
import './styles.scss';

type Props = {
    costCalculation: CostCalculation;
}

const CostCalculationSummary = ({ costCalculation }: Props) => {
    return (
        <div>
            <h5>Periodo de Apuração:</h5>
            <div>
                <small>{costCalculation.startDate}</small>
                {` até `}
                <small>{costCalculation.endDate}</small>
            </div>
            <h5>Estoque Inicial:</h5>
            <div>
                <small>{costCalculation.stockStartDate}</small>
            </div>
            <h5>Status da Apuração:</h5>
            <div>
                <small>{costCalculation.status}</small>
            </div>
        </div>
    );
}
export default CostCalculationSummary;