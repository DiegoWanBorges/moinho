import { Parameter } from 'core/types/Parameter';
import { useForm } from 'react-hook-form';
import './styles.scss';

function Parameters() {
    const { register, handleSubmit } = useForm<Parameter>();

    const onSubmit = (data: Parameter) => {
        console.log(data)
    }

    return (
        <form className="parameter-main" onSubmit={handleSubmit(onSubmit)}>
            <div className="parameter-content-name">
                <label>Nome da empresa:</label>
                <input
                    type="text"
                    ref={register}
                    name="companyName"
                    className="input-base"
                />
            </div>

            <div className="parameter-content-withou-stock">
                <label>Orderm de produção sem estoque? </label>
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
            
            <div className="parameter-content-type-cost">
                <select name="typeCostUsed" ref={register}>
                    <option value="CUSTO_ULTIMA_ENTRADA">Custo última entrada</option>
                    <option value="CUSTO_MEDIO">Custo Médio</option>
                </select>
            </div>

            <button
                className="btn-base-save parameter-content-btn-save"
            >
                Salvar
            </button>


        </form>
    );
}
export default Parameters;
