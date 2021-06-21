import {  ProductionOrderProducedAverageCost } from 'core/types/CostCalculation';
import './styles.scss';

type Props = {
    averageCostProduction: ProductionOrderProducedAverageCost;
}

const AverageCostProduction = ({ averageCostProduction }: Props) => {
    return (
        <div className="averageCostProduction-main">
           <div className="averageCostProduction-product">
                <small>Produto:</small>
                <h6>{averageCostProduction.name}</h6>
           </div>
           <div className="averageCostProduction-produced">
                <small>Total Produzido:</small>
                <h6>{`${averageCostProduction.totalProduced} ${averageCostProduction.unity}`}</h6>
           </div>
           <div className="averageCostProduction-cost">
                <small>Custo Médio:</small>
                <h6>{`${averageCostProduction.averageCost} `}</h6>
           </div>
        </div>
    );
}
export default AverageCostProduction;