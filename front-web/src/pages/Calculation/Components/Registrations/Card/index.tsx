import { CostCalculation } from 'core/types/CostCalculation';
import { toISOFormatDate } from 'core/utils/utils';
import moment from 'moment';
import { Link } from 'react-router-dom';
import './styles.scss';

type Props = {
    costCalculation: CostCalculation;
    onRemove: (costCalculationId: number) => void;
}
const CostCalculationCard = ({ costCalculation, onRemove }: Props) => {
    return (
        <div className="costCalculation-card">

            <div className="costCalculation-card-ref">
                <small>Ref. Mês:</small>
                <h5>{moment(toISOFormatDate(costCalculation.referenceMonth)).format("MM/YYYY")}</h5>
            </div>
            <div className="costCalculation-card-date">
                <small>Periodo:</small>
                <h5>{`${costCalculation.startDate} até ${costCalculation.endDate}`}</h5>
            </div>
            <div className="costCalculation-card-status">
                <small>Status:</small>
                <h5>{costCalculation.status}</h5>
            </div>

            <div className="costCalculation-card-action">
                <button
                    className="btn btn-outline-secondary costCalculation-card-action-btn costCalculation-card-action-btn-edit">
                    EDITAR
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger costCalculation-card-action-btn"
                    onClick={() => onRemove(costCalculation.id)}
                >
                    EXCLUIR
                </button>
            </div>

        </div>
    );
}
export default CostCalculationCard;
