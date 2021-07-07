import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { OccurrencesResponse } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import OccurrenceCard from '../Card';
import CardLoader from 'core/components/CardLoader';
import './styles.scss';

function OccurrenceList() {
    const [occurrencesResponse, setOccurrencesResponse] = useState<OccurrencesResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage, setLinesPerPage] = useState(10);
    const [isLoading, setIsLoading] = useState(false);

    const getOccurrences = useCallback(() => {
        setIsLoading(true)
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name: name,
            orderBy: "id",
            direction: "DESC"
        }
        makePrivateRequest({ url: '/occurrences', params })
            .then(response => setOccurrencesResponse(response.data))
            .finally(() => {
                setIsLoading(false)
            })
    }, [activePage, name, linesPerPage])

    useEffect(() => {
        getOccurrences();
    }, [getOccurrences])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/occurrences/new");
    }
    const onRemove = (occurrenceId: number) => {
        const confirm = window.confirm("Deseja excluir o grupo selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/occurrences/${occurrenceId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Ocorrência excluida com sucesso!")
                    history.push('/registrations/occurrences')
                    getOccurrences();
                })
                .catch(() => {
                    toast.error("Falha ao excluir ocorrência!")
                    history.push('/registrations/occurrences')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const handleChangeLinesPerPage = (linesPerPage: number) => {
        setActivePage(0);
        setLinesPerPage(linesPerPage)
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
        setLinesPerPage(10);
    }
    return (
        <div className="group-list">
            <div className="group-list-add-filter">
                <button
                    className="btn btn-primary btn-lg group-list-btn-add"
                    onClick={handCreate}
                >
                    ADCIONAR
                </button>
                <Filter
                    name={name}
                    handleChangeName={handleChangeName}
                    linesPerPage={linesPerPage}
                    handleChangeLinesPerPage={handleChangeLinesPerPage}
                    clearFilters={clearFilters}
                    placeholder="Digite o nome da ocorrência"
                />
            </div>
            <div className="admin-list-container">
                {isLoading ? <CardLoader /> : (
                    occurrencesResponse?.content.map(occurrence => (
                        <OccurrenceCard
                            occurrence={occurrence} key={occurrence.id}
                            onRemove={onRemove}
                        />
                    )))}

                {occurrencesResponse &&
                    <Pagination
                        totalPages={occurrencesResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default OccurrenceList;
