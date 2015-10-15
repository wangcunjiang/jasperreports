/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * The Custom Visualization Component program and the accompanying materials
 * has been dual licensed under the the following licenses:
 * 
 * Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Custom Visualization Component is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.jaspersoft.jasperreports.customvisualization.fill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.items.ItemProperty;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.fill.JRFillExpressionEvaluator;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: CVFillItem.java 6002 2013-03-20 08:15:32Z teodord $
 */
public class CVFillItem implements Item {

	/**
	 *
	 */
	protected Item item;
	protected Map<String, Object> evaluatedProperties;

	/**
	 *
	 */
	public CVFillItem(Item item, JRFillObjectFactory factory) {
		factory.put(item, this);

		this.item = item;
	}

	/**
	 *
	 */
	public void evaluateProperties(JRFillExpressionEvaluator evaluator,
			byte evaluation) throws JRException {
		List<ItemProperty> itemProperties = getProperties();
		Map<String, Object> result = null;
		if (itemProperties != null && !itemProperties.isEmpty()) {
			result = new HashMap<String, Object>();
			for (ItemProperty property : itemProperties) {
				result.put(property.getName(),
						getEvaluatedValue(property, evaluator, evaluation));
			}
		}

		evaluatedProperties = result;
	}

	/**
	 *
	 */
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ItemProperty> getProperties() {
		return item.getProperties();
	}

	public Map<String, Object> getEvaluatedProperties() {
		return evaluatedProperties;
	}

	public Object getEvaluatedValue(ItemProperty property,
			JRFillExpressionEvaluator evaluator, byte evaluation)
			throws JRException {
		Object result = null;
		if (property.getValueExpression() == null
				|| "".equals(property.getValueExpression())) {
			result = property.getValue();
		} else {
			result = evaluator.evaluate(property.getValueExpression(),
					evaluation);
		}

		verifyValue(property, result);

		return result;
	}

	public void verifyValue(ItemProperty property, Object value)
			throws JRException {

	}

}
