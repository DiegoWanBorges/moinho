import './styles.scss'
import { StockMovement } from 'core/types/StockMovement'
import { Link } from 'react-router-dom';

type Props = {
    stockMovement: StockMovement;
    onRemove: (stockMovementId:number) =>void;
}

const StockMovementCard = ({ stockMovement,onRemove }: Props) => {
    const typeProduction = ['PRODUCAO_ENTRADA', 'PRODUCAO_CONSUMO', 'PRODUCAO_RETORNO'];

    return (
        <div className="stockMovementCard-main">
            <div className="stockMovementCard-date">
                <h6>{stockMovement.date}</h6>
            </div>
            <div className="stockMovementCard-name">
                <h6 >{stockMovement.product.name}</h6>
                <small>
                    {`Origem: ${stockMovement.type.replace('_', ' ').toLowerCase()}
                     Id:${stockMovement.idOrignMovement}`}
                </small>
            </div>

            <div className="stockMovementCard-quantity">
                {stockMovement.entry === 0 ?
                    (<h6>{`Saida: ${stockMovement.out} ${stockMovement.product.unity.id}`}</h6>)
                    :
                    (<h6>{`Entrada: ${stockMovement.entry} ${stockMovement.product.unity.id}`}</h6>)
                }
            </div>
            <div className="stockMovementCard-cost">
                <h6>{`Custo: ${stockMovement.cost} `}</h6>
            </div>

            <div className={`stockMovementCard-action ${typeProduction.some(item => item === stockMovement.type) ? 'd-none' : ''}`} >
                <Link
                    to={`/stock/movements/${stockMovement.id}`}
                    type="button"
                    className="btn btn-outline-secondary stockMovementCard-action-edit">
                    EDITAR
                </Link>

                <button 
                    className="btn btn-outline-danger stockMovementCard-action-del"
                    onClick={() =>onRemove(stockMovement.id)}
                >
                    Deletar
                </button>

            </div>

        </div>
    )
}
export default StockMovementCard