import { toast } from 'react-toastify';
import { Parameter } from 'core/types/Parameter';
import { makePrivateRequest } from 'core/utils/request';
import { useForm } from 'react-hook-form';
import './styles.scss';
import { useEffect } from 'react';

function Parameters() {
    const { register, handleSubmit, setValue } = useForm<Parameter>();


    useEffect(() => {
           makePrivateRequest({ url: `/parameters/1` })
                .then(response => {
                    setValue('companyName',response.data.companyName)
                    setValue('productionOrderWithoutStock',response.data.productionOrderWithoutStock.toString())
                    setValue('typeCostUsed',response.data.typeCostUsed)
                })
            
    }, [setValue])

    const onSubmit = (data: Parameter) => {
        makePrivateRequest({
            url:  '/parameters/1',
            method:  'PUT',
            data
        })
            .then(() => {
                toast.success("Parâmetro salvo com sucesso!")
            })
            .catch(() => {
                toast.error("Erro ao salvar parâmetro!")
            })
    }

    return (
        <form className="parameter-main" onSubmit={handleSubmit(onSubmit)}>
            <div className="parameter-content-name">
                <label className="label-base">Nome da empresa:</label>
                <input
                    type="text"
                    ref={register}
                    name="companyName"
                    className="input-base"
                />
            </div>

            <div className="parameter-content">
                <label className="label-base">Orderm de produção sem estoque? </label>
                <div className="parameter-content-withou-stock-">
                    <input
                        ref={register}
                        type="radio"
                        value="true"
                        name="productionOrderWithoutStock"
                    /> Sim 
                    <input
                        ref={register}
                        type="radio"
                        value="false"
                        name="productionOrderWithoutStock"
                    /> Não
               </div>

            </div>

            <div className="parameter-content-type-cost">
                <label className="label-base">Tipo de custo? </label>
                <select name="typeCostUsed" ref={register} className="parameter-content-type-cost-select">
                    <option value="CUSTO_ULTIMA_ENTRADA">Custo última entrada</option>
                    <option value="CUSTO_MEDIO">Custo Médio</option>
                </select>
            </div>
            <div className="parameter-content-btn">
                <button
                    className="btn btn-primary parameter-content-btn-save "
                >
                    Salvar
            </button>
            </div>



        </form>
    );
}
export default Parameters;
