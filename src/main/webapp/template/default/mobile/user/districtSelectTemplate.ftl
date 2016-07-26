<#if districtSelections??>
	<select onchange="getNextLevelDistrcitSelections(this.value, '${nextLiId}', '${canGetNextDistrictSelections?c}')">
		<option value="-1">-请选择-</option>
		<#list districtSelections as district>
			<option value="${district.id?c}">${district.name!''}</option>
		</#list>
	</select>
</#if>