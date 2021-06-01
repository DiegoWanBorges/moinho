import './styles.scss'
import { Link } from 'react-router-dom';
import {  OperationalPayment } from 'core/types/Payment';

type Props = {
    operationalPayment: OperationalPayment;
    onRemove: (operationalPaymentId: number) => void;
}

const OperationalPaymentCard = ({ operationalPayment, onRemove }: Props) => {
    return (
        <div className="operationalPaymentCard-main">
            <div className="operationalPaymentCard-date">
                <h6>{operationalPayment.date}</h6>
            </div>
            <div className="operationalPaymentCard-name">
                <h6>{operationalPayment.operationalCostType.name}</h6>
                <small>{operationalPayment.operationalCostType.type}</small>
            </div>
            <div className="operationalPaymentCard-value">
                 <h6>R${operationalPayment.value}</h6>
            </div>

            <div className="operationalPaymentCard-action">
                <Link
                    to={`/payments/operational/${operationalPayment.id}`}
                    type="button"
                    className="btn btn-outline-secondary operationalPaymentCard-action-edit">
                    EDITAR
                </Link>

                <button
                    className="btn btn-outline-danger operationalPaymentCard-action-del"
                    onClick={() => onRemove(operationalPayment.id)}
                >
                    Deletar
                </button>
            </div>
        </div>
    )
}
export default OperationalPaymentCard