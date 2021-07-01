import { ProductionOrder } from 'core/types/ProductionOrder';
import history from 'core/utils/history';
import { makePrivateRequest } from 'core/utils/request';
import { toast } from 'react-toastify';
import './styles.scss';
import Print from 'core/assets/images/print.png'

type Props = {
    productionOrder: ProductionOrder;
    onRemove: (productionOrderId: number) => void;
}
const ProductionOrderCard = ({ productionOrder, onRemove }: Props) => {
    const onEdit = () => {
        history.push(`/productions/registrations/${productionOrder.id}`)
    }
    const onPrint = () => {
        makePrivateRequest({ url: `/productionorders?pdf=${productionOrder.id}`, responseType: "blob" })
            .then(response => {
                //Build a URL from the file
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                //Open the URL on new Window
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
    }
    return (
        <div className="ProductionOrderCard-card">

            <div className="ProductionOrderCard-card-inf">
                <h5>{`O.P: ${productionOrder.id} - ${productionOrder.formulation.description}`}</h5>
                <small>{`Dt. Inicio: ${productionOrder.startDate === null ? `-` : productionOrder.startDate}`}</small>
                <small>{` Dt. Fim: ${productionOrder.endDate === null ? `-` : productionOrder.endDate}`}</small>
                <h6 >{productionOrder.status}</h6>
            </div>


            <div className="ProductionOrderCard-card-action">
                <img
                    className="formulation-form-btn-print"
                    src={Print} alt=""
                    onClick={onPrint}
                />

                <button
                    onClick={onEdit}
                    className="btn btn-outline-secondary ProductionOrderCard-card-action-edit ">
                    {productionOrder.status === "APURACAO_FINALIZADA" ? "VISUALIZAR" : "EDITAR"}
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger ProductionOrderCard-card-action-save"
                    onClick={() => onRemove(productionOrder.id)}
                >
                    EXCLUIR
                </button>
            </div>

        </div>
    );
}
export default ProductionOrderCard;
