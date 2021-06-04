import './styles.scss'
import { Link } from 'react-router-dom';
import { LaborPayment } from 'core/types/Payment';

type Props = {
    laborPayment: LaborPayment;
    onRemove: (laborPaymentId: number) => void;
}

const LaborPaymentCard = ({ laborPayment, onRemove }: Props) => {


    return (
        <div className="laborPaymentCard-main">
            <div className="laborPaymentCard-date">
                <h6>{laborPayment.date}</h6>
            </div>
            <div className="laborPaymentCard-name">
                <h6>{laborPayment.employee.name}</h6>
                <small>{laborPayment.employee.sector.name} - {laborPayment.laborCostType.name}</small>
            </div>
            <div className="laborPaymentCard-value">
                 <h6>R${laborPayment.value}</h6>
            </div>
            



            <div className="laborPaymentCard-action">
                <Link
                    to={`/payments/labor/${laborPayment.id}`}
                    type="button"
                    className="btn btn-outline-secondary laborPaymentCard-action-edit">
                    EDITAR
                </Link>

                <button
                    className="btn btn-outline-danger laborPaymentCard-action-del"
                    onClick={() => onRemove(laborPayment.id)}
                >
                    Deletar
                </button>
            </div>
        </div>
    )
}
export default LaborPaymentCard