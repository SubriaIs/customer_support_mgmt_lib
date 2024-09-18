package org.swfias.daos;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swfias.dtos.CaseDto;
import org.swfias.enums.SeverityType;
import org.swfias.enums.StatusType;
import org.swfias.interfaces.DaoBase;
import org.swfias.interfaces.DaoCases;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaseDao extends DaoBase<CaseDto> implements DaoCases {
    protected static final Logger logger = LogManager.getLogger(CaseDao.class);

    private static final String Query_GETALL = "select * from cases";
    private static final String Query_GETALLBYID = "select * from cases where createdBy= ?";
    private static final String Query_GETALLBY_TITLE = "select * from cases where title = ?";
    private static final String Query_GETALLBYASSIGNEDTOID = "select * from cases where assignedTo= ?";
    private static final String Query_UPDATE_CASE = "UPDATE cases SET resolutionDetails = ?, resolvedDate = ?, status = ? WHERE id = ?";
    private static final String Query_SAVE = "insert into cases (createdBy, title, description, createdDate, status, severity, assignedTo, resolutionDetails, resolvedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


    @Override
    public Optional<CaseDto> getById(long userId) throws SQLException {
        return Optional.empty();
    }


    @Override
    public List<CaseDto> getAll() throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(Query_GETALL);
        List<CaseDto> ls = new ArrayList<>();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CaseDto caseDto = new CaseDto();
            caseDto.setId(rs.getLong("id"));
            caseDto.setCreatedBy(rs.getLong("createdBy"));
            caseDto.setTitle(rs.getString("title"));
            caseDto.setDescription(rs.getString("description"));
            caseDto.setCreatedDate(rs.getDate("createdDate"));
            caseDto.setStatus(StatusType.valueOf(rs.getString("status")));
            caseDto.setSeverity(SeverityType.valueOf(rs.getString("severity")));
            caseDto.setAssignedTo(rs.getLong("assignedTo"));
            caseDto.setResolutionDetails(rs.getString("resolutionDetails"));
            caseDto.setResolvedDate(rs.getDate("resolvedDate"));
            ls.add(caseDto);
        }
        logger.info(" Found cases information in total: " + ls.size());
        return ls;
    }

    @Override
    public void save(CaseDto caseDto) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(Query_SAVE);

        ps.setLong(1, caseDto.getCreatedBy());
        ps.setString(2, caseDto.getTitle());
        ps.setString(3, caseDto.getDescription());
        ps.setDate(4, (Date) caseDto.getCreatedDate());
        ps.setString(5, caseDto.getStatus().toString());
        ps.setString(6, caseDto.getSeverity().toString());
        ps.setLong(7, caseDto.getAssignedTo());
        ps.setString(8, caseDto.getResolutionDetails());
        ps.setDate(9, (Date) caseDto.getResolvedDate());

        int n = ps.executeUpdate();
        if (n == 1) {
            logger.log(Level.INFO, "cases added successfully.");
        } else {
            logger.log(Level.WARN, "Ops! Problem occurred.");
        }

    }

    @Override
    public void update(CaseDto caseDto, String[] params) {


    }

    @Override
    public void delete(CaseDto caseDto) throws SQLException {

    }

    @Override
    public List<CaseDto> getAllById(long userId) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(Query_GETALLBYID);
        ps.setString(1, String.valueOf(userId));
        List<CaseDto> ls = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CaseDto caseDto = new CaseDto();
            caseDto.setId(rs.getLong("id"));
            caseDto.setCreatedBy(rs.getLong("createdBy"));
            caseDto.setTitle(rs.getString("title"));
            caseDto.setDescription(rs.getString("description"));
            caseDto.setCreatedDate(rs.getDate("createdDate"));
            caseDto.setStatus(StatusType.valueOf(rs.getString("status")));
            caseDto.setSeverity(SeverityType.valueOf(rs.getString("severity")));
            caseDto.setAssignedTo(rs.getLong("assignedTo"));
            caseDto.setResolutionDetails(rs.getString("resolutionDetails"));
            caseDto.setResolvedDate(rs.getDate("resolvedDate"));
            ls.add(caseDto);
        }
        return ls;
    }

    @Override
    public List<CaseDto> getAllByAssignToId(long assignedTo) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(Query_GETALLBYASSIGNEDTOID);
        ps.setString(1, String.valueOf(assignedTo));
        List<CaseDto> ls = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CaseDto caseDto = new CaseDto();
            caseDto.setId(rs.getLong("id"));
            caseDto.setCreatedBy(rs.getLong("createdBy"));
            caseDto.setTitle(rs.getString("title"));
            caseDto.setDescription(rs.getString("description"));
            caseDto.setCreatedDate(rs.getDate("createdDate"));
            caseDto.setStatus(StatusType.valueOf(rs.getString("status")));
            caseDto.setSeverity(SeverityType.valueOf(rs.getString("severity")));
            caseDto.setAssignedTo(rs.getLong("assignedTo"));
            caseDto.setResolutionDetails(rs.getString("resolutionDetails"));
            caseDto.setResolvedDate(rs.getDate("resolvedDate"));
            ls.add(caseDto);
        }
        return ls;
    }

    @Override
    public CaseDto getByTitle(String title) throws SQLException {
        CaseDto caseDto = null;
        try (PreparedStatement ps = this.connection.prepareStatement(Query_GETALLBY_TITLE)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    caseDto = new CaseDto();
                    caseDto.setId(rs.getLong("id"));
                    caseDto.setCreatedBy(rs.getLong("createdBy"));
                    caseDto.setTitle(rs.getString("title"));
                    caseDto.setDescription(rs.getString("description"));
                    caseDto.setCreatedDate(rs.getDate("createdDate"));
                    caseDto.setStatus(StatusType.valueOf(rs.getString("status")));
                    caseDto.setSeverity(SeverityType.valueOf(rs.getString("severity")));
                    caseDto.setAssignedTo(rs.getLong("assignedTo"));
                    caseDto.setResolutionDetails(rs.getString("resolutionDetails"));
                    caseDto.setResolvedDate(rs.getDate("resolvedDate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return caseDto;

    }

    public boolean updateCase(CaseDto caseDto) {
        try (PreparedStatement ps = connection.prepareStatement(Query_UPDATE_CASE)) {
            ps.setString(1, caseDto.getResolutionDetails());
            ps.setDate(2, caseDto.getResolvedDate());
            ps.setString(3, String.valueOf(caseDto.getStatus()));
            ps.setLong(4, caseDto.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTitleDuplicate(String title) throws SQLException {
        String query = "SELECT COUNT(*) FROM cases WHERE title = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }
}
