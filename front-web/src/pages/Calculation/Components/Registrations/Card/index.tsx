import { CostCalculation } from 'core/types/CostCalculation';
import history from 'core/utils/history';
import { toISOFormatDate } from 'core/utils/utils';
import moment from 'moment';

import './styles.scss';

type Props = {
    costCalculation: CostCalculation;
    onRemove: (costCalculationId: number) => void;
}

const CostCalculationCard = ({ costCalculation, onRemove }: Props) => {
    const view =()=>{
        history.push(`/calculations/registrations/${costCalculation.id}`)
    }
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
                    onClick={view}
                    className="btn btn-outline-secondary costCalculation-card-action-btn costCalculation-card-action-btn-edit">
                    VISUALIZAR
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
