import { FormulationItem } from 'core/types/Formulation';
import './styles.scss';
import Modal from 'react-modal';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { ProductionOrderItem } from 'core/types/ProductionOrder';

type Props = {
    productionOrderItem: ProductionOrderItem;
    onEditItem: (productionOrderItem: ProductionOrderItem) => void;
}
function ProductionOrderItemEditModal({ productionOrderItem, onEditItem }: Props) {
    const { register, errors, handleSubmit, setValue } = useForm<FormulationItem>();
    const [show, setShow] = useState(false);

    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setShow(!show)
    }
    const customStyles = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };
    function afterOpenModal() {
        setValue('quantity', productionOrderItem.quantity);
    }

    const onSave = (data: ProductionOrderItem) => {
        const payLoad ={
            ...data,
            product:productionOrderItem.product,
            productionOrderId:productionOrderItem.productionOrderId,
            serie:productionOrderItem.serie
        }
        onEditItem(payLoad);
        setShow(!show);
    }
    return (
        <>
            <button
                className="btn btn-primary btn-sm formulation-item-card-actions-btn-edit"
                onClick={e => onEdit(e)}
            >
                Editar
            </button>

            <Modal
                isOpen={show}
                style={customStyles}
                onAfterOpen={afterOpenModal}
                ariaHideApp={false}
            >
                <h5>{productionOrderItem.product.name}</h5>
                <small>Serie:{productionOrderItem.serie} - Tipo:{productionOrderItem.type}</small>

                <div className="formulationItemEdit-row">
                    <div className="formulationItemEdit-quantity">
                        <label className="label-base">Quantidade:</label>
                        <input
                            className="input-base"
                            name="quantity"
                            type="number"
                            ref={register({
                                required: "Campo obrigatório",
                                min: { value: 0.001, message: "O valor dever ser maior que zero" },
                            })}
                        />
                        {errors.quantity && (
                            <div className="invalid-feedback d-block">
                                {errors.quantity.message}
                            </div>
                        )}
                    </div>
                    
                </div>
                <div>
                        
                    <div className="formulationItemEdit-btn">
                        <button
                            className="btn btn-primary formulationItemEdit-btn-save"
                            onClick={handleSubmit(onSave)}                    >
                            Salvar
                    </button>

                    <button
                            className="btn btn-danger formulationItemEdit-btn-del"
                            onClick={() => setShow(!show)}
                        >
                            Cancelar
                    </button>
                    </div>
                    </div>
            </Modal>
        </>
    );
}
export default ProductionOrderItemEditModal;
